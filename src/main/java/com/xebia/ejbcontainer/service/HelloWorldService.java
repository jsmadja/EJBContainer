package com.xebia.ejbcontainer.service;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class HelloWorldService implements IHelloWorldService {

    @Override
    public String sayHelloWorld() {
        return "Hello World";
    }

}
