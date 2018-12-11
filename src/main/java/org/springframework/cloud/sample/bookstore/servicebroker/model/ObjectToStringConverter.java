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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectToStringConverter implements AttributeConverter<Object, String> {

	/**
	 * 数字正则表达式
	 */
	private static final Pattern NUMBER_PATTERN = Pattern.compile("^(\\-)?([1-9]\\d{1}|\\d)(\\.\\d+)?$");

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

	/**
	 * Json 数据转换工具
	 */
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Object o) {

		// 如果是数字，直接以字符串形式返回；如果非数字，普通字符串直接返回
		if (o instanceof Number || o instanceof String) {
			return o.toString();
		}

		// 引用类型对象转换为 JSON 数据返回 JSON 串
		String json = null;
		try {
			json = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}

		return json.toString();
	}

	@Override
	public Object convertToEntityAttribute(String s) {

		// 如果是数字，转换为数字形式返回
		if (isNumeric(s)) {
			return Double.parseDouble(s);
		}

		// 如果非数字，普通字符串直接返回，JSON 数据则转换为对象进行返回
		boolean arrayString = s.startsWith(PARENTHESES_LEFT) && s.endsWith(PARENTHESES_RIGHT);
		boolean objectString = s.startsWith(BRACES_LEFT) && s.endsWith(BRACES_RIGHT);
		if (!arrayString && !objectString) {
			return s;
		}

		// 如果非数字，尝试转换为对象，成功则返回对象，失败则返回字符串
		Object obj = null;
		try {
			obj = mapper.readValue(s, Object.class);
		} catch (IOException e) {
			e.printStackTrace();
			return s;
		}

		return obj;
	}

	/**
	 * 判断是否为数字
	 *
	 * @param str
	 * @return
	 */
	private boolean isNumeric(String str) {

		Matcher isNum = NUMBER_PATTERN.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
