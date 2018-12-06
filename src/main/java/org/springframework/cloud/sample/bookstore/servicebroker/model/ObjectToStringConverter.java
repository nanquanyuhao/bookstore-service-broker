/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.sample.bookstore.servicebroker.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class ObjectToStringConverter implements AttributeConverter<Object, String> {

	/**
	 * 中括号
	 */
	private static final String PARENTHESES_LEFT = "[";
	private static final String PARENTHESES_RIGHT = "]";

	/**
	 * 大括号
	 */
	private static final String BRACES_LEFT = "{";
	private static final String BRACES_RIGHT = "}";

	@Override
	public String convertToDatabaseColumn(Object o) {

		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json.toString();
	}

	@Override
	public Object convertToEntityAttribute(String s) {

		boolean arrayString = s.startsWith(PARENTHESES_LEFT) && s.endsWith(PARENTHESES_RIGHT);
		boolean objectString = s.startsWith(BRACES_LEFT) && s.endsWith(BRACES_RIGHT);

		if (!arrayString && !objectString) {
			return s;
		}

		ObjectMapper mapper = new ObjectMapper();
		Object obj = null;
		try {
			obj = mapper.readValue(s, Object.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
}
