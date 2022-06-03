package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    private final TaskRepository taskRepository;


    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("button", "click");
        context.setVariable("goodbye_message", "thank you");
        context.setVariable("webpages", "http://localhost/tasks_frontend");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("templates.mail/created-trello-card-mail", context);
    }

    public String buildEmail() {

        long size= taskRepository.count();
        List<String> functionality = new ArrayList<>();
        functionality.add("Currently in database you got: " + size + " tasks");
        functionality.add(LocalDate.now().toString());

        Context context = new Context();
        context.setVariable("button", "show tasks");
        context.setVariable("show_button", true);
        context.setVariable("webpages", "http://localhost/tasks_frontend");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("templates.mail/tasks-number-daily-mail", context);
    }
}
