package com.emarbox.dao;

import com.emarbox.mapper.MapperAuthProvider;
import com.rainerhahnekamp.sneakythrow.Sneaky;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class BaseDao<T> {
	@Autowired
	MapperAuthProvider mapperProvider;
	@Autowired
	Mapper dozerBeanMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T beanSupply(T oldRecord, T newRecord, Class<T> cls) {
		Map currentMap = Sneaky.sneaked(() -> objectToMap(oldRecord)).get();
		Map newMap = Sneaky.sneaked(() -> objectToMap(newRecord)).get();
		for (Object key : newMap.keySet()) {
			boolean hasLength = Objects.nonNull(newMap.get(key)) && !newMap.get(key).equals("null");
			if (hasLength) {
				currentMap.put(key, newMap.get(key));
			}
		}
		return dozerBeanMapper.map(currentMap, cls);
	}

	public Map<String, Object> objectToMap(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (key.compareToIgnoreCase("class") == 0) {
				continue;
			}
			Method getter = property.getReadMethod();
			Object value = getter != null ? getter.invoke(obj) : null;
			map.put(key, value);
		}

		return map;
	}
}
