/**
 * @Author Bryan Zen 113252725
 * @version 1.0
 * @since 2021-11-30
 */

public class CityNotFoundException extends Exception{
    /**
     * Thrown when the input city is not found in the hash map
     * @param message Extends Exception class, returns a string output.
     */
    public CityNotFoundException(String message){
        super(message);
    }
}
