package guide.jwtservice.utils;

public record HttpResponse<T> (boolean success, String message, T data) { }
