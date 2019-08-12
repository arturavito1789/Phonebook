
package com.mycompany.phonebook.service;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;


public class BinTelegram {
    public static <T> T getBeanInstance( BeanManager bm_, Class<T> class_ )
    {
      Bean<T> bean = (Bean<T>) bm_.getBeans( class_ ).iterator().next();
      CreationalContext<T> ctx = bm_.createCreationalContext( bean );
      T object = (T) bm_.getReference(bean, class_, ctx);
      return object;
    }  
}
