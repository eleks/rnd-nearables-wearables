package com.eleks.controller;

import static com.eleks.utils.Constants.EmployeesControllerConstants.EMPLOYEES_URL;
import static com.eleks.utils.Constants.EmployeesControllerConstants.EMPLOYEE_IMAGE_URL;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.apache.http.HttpResponse;
import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.stream.EntityState;
import org.apache.james.mime4j.stream.MimeTokenStream;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eleks.exception.InternalServerException;
import com.eleks.exception.NotFoundException;
import com.eleks.model.teampro.Employee;
import com.eleks.service.EmployeesService;
import com.eleks.service.HttpService;
import com.eleks.service.SessionService;
import com.eleks.utils.Constants;
import com.eleks.validator.AccessTokenValidator;

@RestController
public class EmployeesController {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmployeesController.class);
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private HttpService httpService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@ModelAttribute("access_token")
	private String idAsModelAttribute(@RequestParam(value = "access_token") String accessToken) {
	  return accessToken;
	}
	
	@RequestMapping(value = EMPLOYEES_URL, method = RequestMethod.GET)
	public List<Employee> getEmployees(@ModelAttribute(value = "access_token") @Validated(AccessTokenValidator.class) String accessToken) {
		return employeesService.getEmployeesFromTeamProServer();
	}
	
	@RequestMapping(value = EMPLOYEE_IMAGE_URL, method = RequestMethod.GET, produces = "image/jpg")
	public byte[] getImageUrl(@Valid @RequestHeader(value = "access_token") String accessToken, 
			@PathVariable(value = "id") long id, @RequestParam(required = false, value = "size", defaultValue = "0") int size) {
		LOG.debug("Started");
		
		try {
			BufferedImage bufferedImage = null;
			
			final HttpResponse httpResponse = httpService.executeRequest(Constants.TEAMPRO_EMPLOYEES_IMAGE_URL + id, Constants.DEFAULT_CREDENTIALS);
			
			final MimeTokenStream stream = new MimeTokenStream();
			stream.parse(httpResponse.getEntity().getContent());
			for (EntityState state = stream.getState(); state != EntityState.T_END_OF_STREAM; state = stream.next()) {
				switch (state) {
				case T_BODY:
					final String mediaType = stream.getBodyDescriptor().getMediaType();
					if("image".equals(mediaType)) {
						final InputStream inputStream = stream.getDecodedInputStream();
						if(bufferedImage == null && inputStream != null) {
							try {
								bufferedImage = ImageIO.read(inputStream);
								
								if(size == 1) {
									final int width = bufferedImage.getWidth();
									final int height = bufferedImage.getHeight();
									if(width < height) {
										final int topY = (height - width) / 2;
										bufferedImage = bufferedImage.getSubimage(0, topY, width, width);
									} else if (width > height){
										final int topX = (width - height) / 2;
										bufferedImage = bufferedImage.getSubimage(topX, 0, height, height);
									}
									
									bufferedImage = Scalr.resize(bufferedImage, 150, 150);
								} else {
									bufferedImage = Scalr.resize(bufferedImage, 600, 600);
								}
								
							} catch (IOException e) {
								LOG.error(e.getMessage(), e);
							}
						}
					}
					
					break;
				default:
					break;
				}
			}
	        
	        if(bufferedImage == null) {
	        	throw new NotFoundException("Employee photo not found");
	        }
	        
	        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
	        
	        LOG.debug("Finished");

	        return byteArrayOutputStream.toByteArray();
	    } catch (IOException | MimeException e) {
	    	LOG.error(e.getMessage(), e);
	    	throw new InternalServerException("Error occured");
	    }
	}
}
