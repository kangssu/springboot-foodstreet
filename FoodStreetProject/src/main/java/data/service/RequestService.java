package data.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import data.dto.RequestDto;
import data.mapper.RequestMapper;

@Service
public class RequestService {

  private final RequestMapper mapper;

  public RequestService(RequestMapper mapper) {
    this.mapper = mapper;
  }

  public void insertRequest(RequestDto dto) throws IllegalStateException, IOException {

    String projectpath = "/home/tomcat/apache-tomcat-9.0.58/webapps/foodstreet/images";
    UUID uuid = UUID.randomUUID();

    String photoName = "";
    for (MultipartFile file : dto.getUpload()) {
      String fileName = uuid + "_" + file.getOriginalFilename();
      photoName += fileName + ",";

      File saveFile = new File(projectpath, fileName);

      file.transferTo(saveFile);
    }

    photoName = photoName.substring(0, photoName.length() - 1);

    dto.setImg_name(photoName);
    dto.setImg_path("/images/" + photoName);

    mapper.insertRequest(dto);
  }
}
