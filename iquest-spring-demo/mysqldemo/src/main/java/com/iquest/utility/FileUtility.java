package com.iquest.utility;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Component;

import com.iquest.pojo.SimpleUser;
import com.iquest.pojo.SimpleUserList;

@Component
public class FileUtility {

	private static final String PATH_TO_RESOURCES = 
			Paths.get("src/main/resources").toAbsolutePath().toString();
	private static final Logger LOGGER = 
			Logger.getLogger(FileUtility.class.getName());
		
	public void saveUserToFileOnDesktop(String fileName, SimpleUser user) {
		try {
			File file = new File(PATH_TO_RESOURCES + "\\" + fileName);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(SimpleUser.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(user, file);
			
			LOGGER.info("The file " + fileName + " has been created");

		 } catch (JAXBException e) {
			 LOGGER.warning("The file could not be created");
		 }
	}
	
	public void saveUserListToFileOnDesktop(String fileName, SimpleUserList userList) {
		try {
			File file = new File(PATH_TO_RESOURCES + "\\" + fileName);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(SimpleUserList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(userList, file);
			
			LOGGER.info("The file " + fileName + " has been created");

		 } catch (JAXBException e) {
			 LOGGER.warning("The file could not be created");
		 }
	}
}
