package org.donnchadh.gaelbot.webapp;

import java.security.Principal;
import java.util.List;

import org.donnchadh.gaelbot.domainmodel.Document;
import org.donnchadh.gaelbot.persistence.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DocumentsController {

    private DocumentService documentService;

    @Autowired
    public DocumentsController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ModelAttribute("bookings")
    public List<Document> index(SearchCriteria searchCriteria, Principal currentUser) {
    	if (currentUser != null) {
    	    return documentService.findDocuments(currentUser.getName());
    	} else {
    	    return null;
    	}
    }

    @RequestMapping(method = RequestMethod.GET)
    @ModelAttribute("hotels")
    public List<Document> search(SearchCriteria criteria) {
        return documentService.findDocuments(criteria);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Document show(@RequestParam("id")
            Long id) {
        return documentService.findDocumentById(id);
    }

}
