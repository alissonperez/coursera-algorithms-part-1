/**
 * Estatísticas dos testes com o experimento percolation
 * http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 * https://github.com/alissonperez/coursera-percolation
 *
 * @author Alisson R. Perez
 */
public class PercolationStats {

	// Grid size (n) and num. of operations (t)
	private int _n ;
	private int _t ;

	private double _mean ;
	private double _stddev ;
	private double[] _confidence ;

	private boolean _initialized ;

	/**
	 * Perform T independent computational experiments on an N-by-N grid
	 *
	 * @param  n Size
	 * @param  t Operations
	 * @return
	 */
	public PercolationStats( int n , int t ) {
		_n = n ;
		_t = t ;

		_initialized = false ;
		if ( _n <= 0 || _t <= 0 )
			throw new IllegalArgumentException( "N e T devem ser superiores à 0" ) ;
	}

	/**
	 * Sample mean of percolation threshold
	 *
	 * @return double
	 */
	public double mean() {
		_initialize() ;
		return _mean ;
	}

	/**
	 * Sample standard deviation of percolation threshold
	 *
	 * @return double
	 */
	public double stddev() {
		_initialize() ;
		return _stddev ;
	}

	/**
	 * Returns lower bound of the 95% confidence interval
	 *
	 * @return double
	 */
	public double confidenceLo() {
		_initialize() ;
		return _confidence[0] ;
	}

	/**
	 * Returns upper bound of the 95% confidence interval
	 *
	 * @return double
	 */
	public double confidenceHi() {
		_initialize() ;
		return _confidence[1] ;
	}

	/**
	 * Inicializa a classe
	 */
	private void _initialize() {
		if ( _initialized ) {
			return ;
		}

		double[] fractions = _getFractions() ;

		_mean = StdStats.mean( fractions ) ;
		_stddev = StdStats.stddev( fractions ) ;

		_confidence = new double[2] ;
		_confidence[0] = _mean - ( 1.96 * _stddev / Math.sqrt( _t ) ) ;
		_confidence[1] = _mean + ( 1.96 * _stddev / Math.sqrt( _t ) ) ;

		_initialized = true ;
	}

	/**
	 * Retorna as frações de sites abertos em relação ao tamanho do grid para o número de operações (t)
	 * Ex:
	 * Grid de tamanho 20x20 (400 casas)
	 * O grid filtra (de "percolates") ao abrir a 240th casa, portanto a fração é 0.51 (204/400)
	 * @return double[]
	 */
	private double[] _getFractions() {
		double[] fractions = new double[_t] ;
		int n2 , operations , row , col ;

		n2 = _n*_n ;
		operations = 0 ;

		Percolation o ;

		for ( int i = 0 ; i < _t ; i++ ) {
			operations = 0 ;
			o = new Percolation( _n ) ;

			while ( ! o.percolates() ) {
				row = StdRandom.uniform( _n ) + 1 ;
				col = StdRandom.uniform( _n ) + 1 ;

				if ( o.isOpen( row , col ) ) continue ;

				operations++ ;
				o.open( row , col ) ;
			}

			fractions[i] = operations / (double) n2 ;
		}

		return fractions ;
	}

	public static void main( String[] args ) {
		PercolationStats ps = new PercolationStats( Integer.parseInt( args[0] ) , Integer.parseInt( args[1] ) ) ;

		System.out.printf( "%-24s = %f\n" , "mean" , ps.mean() ) ;
		System.out.printf( "%-24s = %f\n" , "stdev" , ps.stddev() ) ;
		System.out.printf( "%-24s = %f, %f\n" , "95% confidence interval" , ps.confidenceLo() , ps.confidenceHi() ) ;
	}

}