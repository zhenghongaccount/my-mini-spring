package org.springframework.test.common;

import org.springframework.beans.factory.FactoryBean;

import java.util.HashSet;
import java.util.Set;

public class ConvertersFactoryBean implements FactoryBean<Set<?>> {
    @Override
    public Set<?> getObject() throws Exception {
        HashSet<Object> objects = new HashSet<>();
        StringToLocalDateConverter stringToLocalDateConverter = new StringToLocalDateConverter("yyyy-MM-dd");
        objects.add(stringToLocalDateConverter);
        return objects;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
