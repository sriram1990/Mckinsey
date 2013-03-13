import java.util.*;

public class Sort {
	int size, k, v;
	Map<Integer,Integer> fMap=new HashMap<Integer, Integer>();
	
	
	
	public void getInput()
	{
		
		System.out.println("Array Size:");
		Scanner s = new Scanner(System.in);
		size = s.nextInt();

		System.out.println("Enter" +size + "integers");
		for(int i=0 ; i<size ; i++)
		{
			try
			{
				k = s.nextInt();
			}
			catch(InputMismatchException e)
			{
				System.out.println("Only intgeres are allowed");
				System.exit(0);
			}

			 if(fMap.containsKey(k))
			 {
				 v = fMap.get(k);
				 fMap.put(k, ++v);
			 }
			 else
			 {
				 fMap.put(k, 1);
			 }
		}
		
		s.close();
		
	}
	
	public void sort(){
		
		
		List<Map.Entry<Integer, Integer>> l = new LinkedList<Map.Entry<Integer, Integer>>( fMap.entrySet() );

        Collections.sort( l, new Comparator<Map.Entry<Integer, Integer>>()
        {
            public int compare( Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b )
            {
                return (a.getValue()).compareTo( b.getValue() );
            }
        } );
        
    	
        
        System.out.println("Increasing Order sorted by frequency");
        for(Map.Entry<Integer, Integer> entry : l)
        {
        	System.out.println(entry.getKey() + " occured " + entry.getValue() + " times");
        }

	}

	
	public static void main(String[] args) {
		
		Sort s= new Sort();
		s.getInput();
		s.sort();

	}

}
