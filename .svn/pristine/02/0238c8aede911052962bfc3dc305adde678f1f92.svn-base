package com.emarbox.common;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertiserManagerApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Test
	public void contextLoads() {
		for(int i=0;i<6;i++){
			int key = new Random().nextInt(500);
			System.out.println(" int = "+key);
			String toutiao = stringRedisTemplate.opsForHash().get("TT_CONTENT_INFO", "content_"+key).toString();
			System.out.println(" toutiao =" + toutiao);
		}
	}

}

