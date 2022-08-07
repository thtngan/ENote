package BE.Shared;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConnectCloud {


  public Map uploadFile(File uploadedFile) {
    try {
      Cloudinary cloudinary = new Cloudinary("cloudinary://442156244825116:ZIPH53yBDU9ekwPBpqk3UV4UpPw@dhd5h2a7n");

      Map uploadResult = cloudinary.uploader()
          .uploadLarge(
              uploadedFile,
              ObjectUtils.asMap(
                  "use_filename", "true",
                  "resource_type", "auto",
                  "overwrite", true,
                  "public_id", uploadedFile.getName())
          );

//      uploadResult.forEach((key, value) -> System.out.println(key + ":" + value));

      return uploadResult;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}
