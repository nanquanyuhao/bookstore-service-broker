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

import net.sf.json.JSONObject;

import javax.persistence.AttributeConverter;
import java.util.Map;

public class ObjectToStringConverter implements AttributeConverter<Object, String> {
	@Override
	public String convertToDatabaseColumn(Object attribute) {

		JSONObject json = null;
		if (attribute instanceof Map){
			json = JSONObject.fromObject(attribute);
			return json.toString();
		}
		return attribute.toString();
	}

	@Override
	public Object convertToEntityAttribute(String dbData) {

		if (dbData.startsWith("{")) {
			JSONObject json = JSONObject.fromObject(dbData);
			return json;
		}

		return dbData;
	}
}
