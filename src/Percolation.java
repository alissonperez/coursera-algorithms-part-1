/**
 * Percolation
 * http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 * https://github.com/alissonperez/coursera-percolation
 *
 * @author Alisson R. Perez
 */
public class Percolation {

	private int _n ; // Size

	private boolean[] _open_sites ; // Nós abertos.

	private WeightedQuickUnionUF _uf ; // UnionFind implementation

	private int _top_site ; // Nó superior que liga toda a linha superior do grid
	private int _bottom_site ; // Nó inferior que liga toda a linha inferior do grid

	/**
	 * Create N-by-N grid, with all sites blocked
	 *
	 * @param  n Tamanho
	 * @return
	 */
	public Percolation( int n ) {
		_n = n ;
		_initialize() ;
	}

	/**
	 * Inicializa
	 */
	private void _initialize() {
		int n2 = _n*_n ;
		int i ;

		_open_sites = new boolean[n2] ;
		for ( i = 0 ; i < n2 ; i++ ) {
			_open_sites[i] = false ;
		}

		_top_site = n2 ;
		_bottom_site = n2 + 1 ;

		// Adiciona-se 2 para incluir o nó superior e inferior
		_uf = new WeightedQuickUnionUF( n2+2 ) ;
		for ( i = 0 ; i < _n ; i++ ) {
			_uf.union( _top_site , i ) ; // Linha superior
			_uf.union( _bottom_site , n2-i-1 ) ; // Linha inferior
		}
	}

	/**
	 * Open site (row i, column j) if it is not already
	 *
	 * @param i Row
	 * @param j Column
	 */
	public void open( int i , int j )  {
		if ( isOpen( i , j ) ) return ;

		_open_sites[ _getSiteIdx( i , j ) ] = true ;
		_connectNeighborsIfOpen( i , j ) ;
	}

	/**
	 * Conecta o nó aos seus vizinhos, se estiveram abertos..
	 *
	 * @param i Linha
	 * @param j Coluna
	 */
	private void _connectNeighborsIfOpen( int i , int j )  {
		int siteIdx = _getSiteIdx( i , j ) ;

		// Baixo
		if ( i > 1 && isOpen( i-1 , j ) ) _uf.union( siteIdx , _getSiteIdx( i-1 , j ) ) ;

		// Cima
		if ( i < _n && isOpen( i+1 , j ) ) _uf.union( siteIdx , _getSiteIdx( i+1 , j ) ) ;

		// Esquerda
		if ( j > 1 && isOpen( i , j-1 ) ) _uf.union( siteIdx , _getSiteIdx( i , j-1 ) ) ;

		// Direita
		if ( j < _n && isOpen( i , j+1 ) ) _uf.union( siteIdx , _getSiteIdx( i , j+1 ) ) ;
	}

	/**
	 * Is site (row i, column j) open?
	 *
	 * @param  i Linha
	 * @param  j Coluna
	 * @return boolean
	 */
	public boolean isOpen( int i , int j )  {
		return _open_sites[_getSiteIdx( i , j )] ;
	}

	/**
	 * Is site (row i, column j) full? (Tem ligação com a linha superior?)
	 *
	 * @param  i Linha
	 * @param  j Coluna
	 * @return boolean
	 */
	public boolean isFull( int i , int j ) {
		return _uf.connected( _getSiteIdx( i , j ) , _top_site ) ;
	}

	/**
	 * Retorna o índice para o elemento no array dadas as coordenadas.
	 *
	 * @param  i Linha
	 * @param  j Coluna
	 * @return int
	 */
	private int _getSiteIdx( int i , int j ) {
		if ( i < 1 || i > _n || j < 1 || j > _n ) {
			throw new IllegalArgumentException(
				"As coordenadas 'i' e 'j' devem estar dentro do grid (NxN)."
			) ;
		}

		// Transformar as cordenadas i e j no indíce do array
		return ( j-1 ) + ( i-1 ) * _n ;
	}

	/**
	 * Does the system percolate?
	 *
	 * @return boolean
	 */
	public boolean percolates() {
		return _uf.connected( _top_site , _bottom_site ) ;
	}
}