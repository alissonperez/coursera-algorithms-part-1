public class PercolationStats {

	public PercolationStats( int n , int t ) {
		// Criação do Percolation...
	}

	public double mean() {
		return 0.5929934999999997 ; // Valor fake, inicialmente...
	}

	public double stddev() {
		return 0.00876990421552567 ; // Valor fake, inicialmente...
	}

	public double confidenceLo() {
		return 0.666217665216461 ;
	}

	public double confidenceHi() {
		return 0.6676773347835391 ;
	}

	public static void main( String[] args ) {
		if ( args.length < 2 ) {
			throw new IllegalArgumentException( "É preciso informar valores para N e T." ) ;
		}

		PercolationStats p = new PercolationStats(
			Integer.parseInt( args[0] ) , // N (Tamanho NxN do grid)
			Integer.parseInt( args[1] ) // T Número de iterações
		) ;

		System.out.print( "mean                    = " ) ;
		System.out.println( p.mean() ) ;

		System.out.print( "stddev                  = " ) ;
		System.out.println( p.stddev() ) ;

		System.out.print( "95% confidence interval = " ) ;
		System.out.print( p.confidenceLo() ) ;
		System.out.print( ", " ) ;
		System.out.println( p.confidenceHi() ) ;
	}

}