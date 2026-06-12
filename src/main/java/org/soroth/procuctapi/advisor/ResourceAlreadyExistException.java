package org.soroth.procuctapi.advisor;


// exception used when resource already exist
public class ResourceAlreadyExistException extends   RuntimeException{
    public ResourceAlreadyExistException(String message){
        super(message);
    }
}
