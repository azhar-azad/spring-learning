package com.azad.cineplex2.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	private ResponseEntity<Object> handleCommonException(Exception ex, HttpStatus httpStatus) {
		
		ApiError apiError = new ApiError(httpStatus, ex.getLocalizedMessage(), ex.getMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * Custom Exception handlers
	 * */
	@ExceptionHandler({ ResourceCreationFailedException.class })
	public ResponseEntity<Object> handleResourceCreationFailedException(ResourceCreationFailedException ex, WebRequest request) {
		
//		String error = ex.getMessage();
//		
//		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), error);
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		
		return handleCommonException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({ RequestBodyEmptyException.class })
	public ResponseEntity<Object> handleRequestBodyEmptyException(RequestBodyEmptyException ex, WebRequest request) {
		
//		String error = ex.getMessage();
//		
//		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
//		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		
		return handleCommonException(ex, HttpStatus.BAD_REQUEST);
	}
	
	// InvalidPathVariableException
	@ExceptionHandler({ InvalidPathVariableException.class })
	public ResponseEntity<Object> handleInvalidPathVariableException(InvalidPathVariableException ex, WebRequest request) {
		
		return handleCommonException(ex, HttpStatus.BAD_REQUEST);
	}
	
	// ResourceNotFoundException
	@ExceptionHandler({ ResourceNotFoundException.class })
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		
		return handleCommonException(ex, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ ClassNotFoundException.class })
	public ResponseEntity<Object> handleClassNotFoundException(ClassNotFoundException ex, WebRequest request) {
		
		return handleCommonException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	/**
	 * Handle the scenarios of a client sending an invalid request to the API.
	 * 
	 * BindException - This exception is thrown when fatal binding errors occur.
	 * MethodArgumentNotValidException – This exception is thrown when an argument annotated with @Valid failed validation.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = new ArrayList<>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}

	/**
	 * Handle a custom exception that doesn't have a default implementation in the base class.
	 * 
	 * MissingServletRequestPartException – This exception is thrown when the part of a multipart request is not found. 
	 * MissingServletRequestParameterException – This exception is thrown when the request is missing a parameter.
	 * */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String error = ex.getParameterName() + " parameter is missing";

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * ConstraintViolationException – This exception reports the result of constraint violations.
	 * */
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		
	    List<String> errors = new ArrayList<String>();
	    
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	        errors.add(violation.getRootBeanClass().getName() + " " + 
	          violation.getPropertyPath() + ": " + violation.getMessage());
	    }

	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), errors);
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * TypeMismatchException – This exception is thrown when trying to set bean property with the wrong type. 
	 * MethodArgumentTypeMismatchException – This exception is thrown when method argument is not the expected type.
	 * */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
	    
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * HttpRequestMethodNotSupportedException - This exception occurs when we send a requested with an unsupported HTTP method.
	 * */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, 
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request) {
		
	    StringBuilder builder = new StringBuilder();
	    
	    builder.append(ex.getMethod());
	    builder.append(" method is not supported for this request. Supported methods are ");
	    ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

	    ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * HttpMediaTypeNotSupportedException - This exception occurs when the client sends a request with unsupported media type.
	 * */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex, 
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request) {
		
	    StringBuilder builder = new StringBuilder();
	    
	    builder.append(ex.getContentType());
	    builder.append(" media type is not supported. Supported media types are ");
	    ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

	    ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	/**
	 * Default Handler to catch all type of logic that deals with all other exceptions that don't have specific handlers.
	 * */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occured");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
