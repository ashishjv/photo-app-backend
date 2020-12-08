package com.example.fireAuth_REST_API;

import com.example.fireAuth_REST_API.model.Album;
import com.example.fireAuth_REST_API.service.AlbumService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumServiceTests {

	@Autowired
	private AlbumService albumService;
	private String albumID;

	@Before
	public void saveAlbum(){
		Album album = new Album("Name 1","URL1","name1@email.com",java.time.LocalDate.now());
		//album.setId("1");
		this.albumID = albumService.saveAlbum(album).getId();
	}

	@Test
	public void getAlbumByID(){
		Assert.assertEquals("Name 1",albumService.getAlbumById(this.albumID).getName());
	}

	@Test
	public void  updateAlbum(){
		Album album = albumService.getAlbumById(albumID);
		album.setName("EditedName 1");
		albumService.updateAlbum(album);
		Assert.assertEquals("Name 1",albumService.getAlbumById(this.albumID).getName());
	}

	@After
	public void deleteAlbum(){
		albumService.deleteAlbum(albumID);
	}
}
