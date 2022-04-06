package com.tan.image.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tan.image.externalcalls.ImageSearchExtCalls;
import com.tan.image.utils.ImageSearchConstants;

@Service
public class ImageSearchService {

	@Autowired
	ImageSearchExtCalls extCalls;

	public List<String> parser(String name, String qty) {

		System.out.println("Inside parser");

		String url = extCalls.urlBuilder(name, qty);
		String jsonResp = extCalls.extCalls(url, ImageSearchConstants.METHOD);

		System.out.println("Resp ---> " + jsonResp);

		List<String> imageUrlList = new ArrayList<>();

		try {

			if (jsonResp.contains("{")) {
				JSONObject obj = new JSONObject(jsonResp);
				JSONArray results = obj.optJSONArray("images_results");

				for (int i = 0; i < results.length(); i++) {
					System.out.println(results.getJSONObject(i).getString("original"));
					imageUrlList.add(results.getJSONObject(i).getString("original"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return imageUrlList;
	}

	public String imageCollector(List<String> urls) {

		System.out.println("Inside imageCollector");

		String zipFileName = ImageSearchConstants.ZIPFILENAME;

		File fD = new File(zipFileName);

		fD.delete();

		File f = new File(ImageSearchConstants.PATH);
		f.mkdirs();

		try {

			File zipFile = new File(zipFileName);
			zipFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("total files -----> " + urls.size());

			for (int i = 0; i < urls.size(); i++) {

				try {
					ZipEntry zipEntry = new ZipEntry("image_" + i + ".jpg");
					zos.putNextEntry(zipEntry);
					String urlStr = urls.get(i).trim().replace(" ", "%20");

					byte[] buf = extCalls.extCallsForImage(urlStr, ImageSearchConstants.METHOD);

					zos.write(buf, 0, buf.length);

					zos.closeEntry();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			zos.close();
			fos.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return zipFileName;

	}

}
