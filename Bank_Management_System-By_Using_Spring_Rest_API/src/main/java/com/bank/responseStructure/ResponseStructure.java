package com.bank.responseStructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStructure<T> {

	private int httpstatuscode;
	private String responsemsg;
	private T data;
}
