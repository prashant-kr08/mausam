package com.project.authservice.shared.api.dto.mausam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.authservice.shared.api.dto.mausamerror.MausamErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "success",
    "data",
    "error"
})

@Data
@AllArgsConstructor
public class MausamApiResponse<T> {

	private boolean success;
	private T data;
	private MausamErrorResponse error;

}
