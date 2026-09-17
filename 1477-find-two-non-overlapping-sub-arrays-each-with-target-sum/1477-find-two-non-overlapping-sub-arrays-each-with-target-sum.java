class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = Integer.MAX_VALUE;

        int[] best = new int[n];
        java.util.Arrays.fill(best, INF);

        int left = 0;
        int sum = 0;
        int shortest = INF;
        int ans = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) {
                sum -= arr[left++];
            }

            if (sum == target) {
                int length = right - left + 1;

                if (left > 0 && best[left - 1] != INF) {
                    ans = Math.min(ans, length + best[left - 1]);
                }

                shortest = Math.min(shortest, length);
            }

            best[right] = shortest;
        }

        return ans == INF ? -1 : ans;
    }
}
 
     

   
   
    
       


  
     
  

       
    


        
    
     

     


   
          



         

           
              





     



   
