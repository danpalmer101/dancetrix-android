package uk.co.dancetrix.service;

import uk.co.dancetrix.service.mock.MockClassService;

public class ServiceLocator {

    public static final ClassService CLASS_SERVICE = new MockClassService();

}
