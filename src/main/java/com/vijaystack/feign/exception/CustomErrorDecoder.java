package com.vijaystack.feign.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

	@Override
	public Exception decode(String methodKey, Response response) {

		if (response.status() == 404) {
			return new DownstreamResourceNotFoundException("Requested post was not found");
		}

		return defaultDecoder.decode(methodKey, response);
	}
}