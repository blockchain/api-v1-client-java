## `info.blockchain.api.statistics` package

The `statistics` package contains the `Statistics` class that reflects the functionality documented at at https://blockchain.info/api/charts_api. It makes various network statistics available, such as the total number of blocks in existence, next difficulty retarget block, total BTC mined in the past 24 hours etc.

Example usage:

```java

package test;
import info.blockchain.api.statistics.*;

public class App
{
    public static void main(String[] args) throws Exception
    {
        Statistics stats = new Statistics();
        
    	StatisticsResponse stats = stats.get();
    	System.out.println(String.format("The current difficulty is %s. "
    			+ "The next retarget will happen in %s hours.",
    			stats.getDifficulty(),
    			(stats.getNextRetarget() - stats.getTotalBlocks()) * stats.getMinutesBetweenBlocks() / 60));
    			
        Chart txPerSec = stats.getChart("transactions-per-second", "5weeks", "8hours");
        
        Map<String, Integer> pools = stats.getPools("5weeks");
    }
}

```