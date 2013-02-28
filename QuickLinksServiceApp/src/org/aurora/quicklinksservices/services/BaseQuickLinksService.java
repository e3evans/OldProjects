package org.aurora.quicklinksservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BaseQuickLinksService {
	@Autowired
	@Qualifier(value = "iconnectBaseUrl")
	protected String BASE_ICONNECT_URL;
}