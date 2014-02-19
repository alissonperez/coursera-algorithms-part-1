/**
 * Estatísticas dos testes com o experimento percolation
 * http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 * https://github.com/alissonperez/coursera-percolation
 *
 * @author Alisson R. Perez
 */
public class PercolationStats {

	// Grid size (n) and num. of operations (t)
	private int n ;
	private int t ;

	private double mean ;
	private double stddev ;
	private double[] confidence ;

	private boolean _initialized ;

	/**
	 * Perform T independent computational experiments on an N-by-N grid
	 *
	 * @param  newN Size
	 * @param  newT Operations
	 * @return
	 */
	public PercolationStats( int newN , int newT ) {
		this.n = newN ;
		this.t = newT ;

		_initialized = false ;
		if ( n <= 0 || t <= 0 )
			throw new IllegalArgumentException( "N e T devem ser superiores à 0" ) ;
	}

	/**
	 * Sample mean of percolation threshold
	 *
	 * @return double
	 */
	public double mean() {
		initialize() ;
		return mean ;
	}

	/**
	 * Sample standard deviation of percolation threshold
	 *
	 * @return double
	 */
	public double stddev() {
		initialize() ;
		return stddev ;
	}

	/**
	 * Returns lower bound of the 95% confidence interval
	 *
	 * @return double
	 */
	public double confidenceLo() {
		initialize() ;
		return confidence[0] ;
	}

	/**
	 * Returns upper bound of the 95% confidence interval
	 *
	 * @return double
	 */
	public double confidenceHi() {
		initialize() ;
		return confidence[1] ;
	}

	/**
	 * Inicializa a classe
	 */
	private void initialize() {
		if ( _initialized ) {
			return ;
		}

		double[] fractions = getFractions() ;

		mean = StdStats.mean( fractions ) ;
		stddev = StdStats.stddev( fractions ) ;

		confidence = new double[2] ;
		confidence[0] = mean - ( 1.96 * stddev / Math.sqrt( t ) ) ;
		confidence[1] = mean + ( 1.96 * stddev / Math.sqrt( t ) ) ;

		_initialized = true ;
	}

	/**
	 * Retorna as frações de sites abertos em relação ao tamanho do grid para o número de operações (t)
	 * Ex:
	 * Grid de tamanho 20x20 (400 casas)
	 * O grid filtra (de "percolates") ao abrir a 240th casa, portanto a fração é 0.51 (204/400)
	 * @return double[]
	 */
	private double[] getFractions() {
		double[] fractions = new double[t] ;
		int n2 , operations , row , col ;

		n2 = n*n ;
		operations = 0 ;

		Percolation o ;

		for ( int i = 0 ; i < t ; i++ ) {
			operations = 0 ;
			o = new Percolation( n ) ;

			while ( ! o.percolates() ) {
				row = StdRandom.uniform( n ) + 1 ;
				col = StdRandom.uniform( n ) + 1 ;

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