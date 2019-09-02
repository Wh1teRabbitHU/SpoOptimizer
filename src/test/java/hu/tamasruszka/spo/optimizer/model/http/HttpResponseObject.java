package hu.tamasruszka.spo.optimizer.model.http;

/**
 * This is a general entity for HTTP calls.
 *
 * @param <T> Is the returned data type. (Converted from string using the JsonService)
 */
public class HttpResponseObject<T> {

	/**
	 * The requests status code
	 */
	private int statusCode;

	/**
	 * Only if T type is provided when requesting an URL, otherwise it's null
	 */
	private T response;

	/**
	 * The response data as string.
	 */
	private String responseAsString;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public String getResponseAsString() {
		return responseAsString;
	}

	public void setResponseAsString(String responseAsString) {
		this.responseAsString = responseAsString;
	}
}
