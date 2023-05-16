/*
 * The Java implementation of TechGrow openapi.
 * Copyright (C) 2023-present, open.techgrow.cn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.techgrow.openapi.utils;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author clay
 * @date 2023-05-04
 */
@Slf4j
public class OkHttpUtils {

	private static volatile OkHttpClient okHttpClient;

	private static OkHttpClient getOkHttpClient() {
		if (okHttpClient == null) {
			synchronized (OkHttpUtils.class) {
				if (okHttpClient == null) {
					okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
						.writeTimeout(30, TimeUnit.SECONDS)
						.readTimeout(30, TimeUnit.SECONDS)
						.build();
				}
			}
		}
		return okHttpClient;
	}

	/**
	 * Get
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String get(String url) throws IOException {
		OkHttpClient client = getOkHttpClient();
		Request request = new Request.Builder().url(url).build();
		try {
			Response response = client.newCall(request).execute();
			return Objects.requireNonNull(response.body()).string();
		}
		catch (Exception e) {
			log.error("Http error : ", e);
		}
		return null;
	}

	/**
	 * Get
	 * @param url
	 * @param headers
	 * @return
	 */
	public static String get(String url, Map<String, String> headers) {
		OkHttpClient client = getOkHttpClient();
		Request.Builder builder = new Request.Builder().url(url);
		Request request = addHeader(builder, headers).build();
		try {
			Response response = client.newCall(request).execute();
			return Objects.requireNonNull(response.body()).string();
		}
		catch (IOException e) {
			log.error("Http error : ", e);
		}
		return null;
	}

	/**
	 * Post
	 * @param url
	 * @param data
	 * @return
	 */
	public static String post(String url, String data) {
		OkHttpClient client = getOkHttpClient();
		MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(mediaType, data);
		Request request = new Request.Builder().url(url).post(body).build();
		try {
			Response response = client.newCall(request).execute();
			return Objects.requireNonNull(response.body()).string();
		}
		catch (IOException e) {
			log.error("Http error : ", e);
		}
		return null;
	}

	/**
	 * Post
	 * @param url
	 * @param data
	 * @param headers
	 * @return
	 */
	public static String post(String url, String data, Map<String, String> headers) {
		OkHttpClient client = getOkHttpClient();
		MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(mediaType, data);
		Request.Builder builder = new Request.Builder().url(url);
		Request request = addHeader(builder, headers).post(body).build();
		try {
			Response response = client.newCall(request).execute();
			return Objects.requireNonNull(response.body()).string();
		}
		catch (IOException e) {
			log.error("Http error : ", e);
		}
		return null;
	}

	/**
	 * Add header
	 * @param builder
	 * @param headers
	 * @return
	 */
	private static Request.Builder addHeader(Request.Builder builder, Map<String, String> headers) {
		if (CollectionUtil.isNotEmpty(headers)) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		return builder;
	}

}
