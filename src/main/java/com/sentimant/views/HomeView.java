package com.sentimant.views;


import com.sentimant.entity.SentimentFeedEntity;
import com.sentimant.service.SentimentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Route("")
public class HomeView extends VerticalLayout {


    private TextArea txtPrompt = new TextArea();
    private Grid<SentimentFeedEntity> grid = new Grid<>(SentimentFeedEntity.class, false);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private SentimentService sentimentService;
    public HomeView(SentimentService sentimentService) {

        this.sentimentService = sentimentService;
        H1 title = new H1("Let's analysis sentiment!");
        title.getStyle().set("margin-bottom", "-7px");
        txtPrompt.setWidth("70%");
        txtPrompt.getStyle().set("align", "center");
        txtPrompt.setPlaceholder("Enter you feed here.....");
        txtPrompt.getStyle().set("border-radius", "0.5px");

        Button btnAnalysis = new Button("Submit");
        btnAnalysis.addThemeVariants(ButtonVariant.LUMO_PRIMARY,ButtonVariant.LUMO_SUCCESS);
        btnAnalysis.setEnabled(false);

        txtPrompt.setValueChangeMode(ValueChangeMode.EAGER);
        txtPrompt.addValueChangeListener(e->{

            if(e.getValue() !=null && !e.getValue().isBlank()){
                btnAnalysis.setEnabled(true);
            }else{
                btnAnalysis.setEnabled(false);
            }
        });

        btnAnalysis.addClickListener(e -> {

            if(txtPrompt.getValue() !=null && !txtPrompt.getValue().isBlank()){
                this.sentimentService.save(txtPrompt.getValue());
                loadItems();
            }

        });

        VerticalLayout parentLayout = new VerticalLayout(title, txtPrompt, btnAnalysis);
        parentLayout.setWidthFull();
        parentLayout.getStyle().set("align-items", "center");
        parentLayout.getStyle().set("align-self", "center");
        parentLayout.setPadding(false);
        parentLayout.setWidth("80%");
        this.add(parentLayout, grid);

        implementGridAndLoadData();
    }

    private void implementGridAndLoadData() {
        grid.addColumn(SentimentFeedEntity::getContent).setHeader("Content").setAutoWidth(true);
        grid.addColumn(SentimentFeedEntity::getSentimentScore).setHeader("Sentiment Score").setWidth("100px");
        grid.addColumn(SentimentFeedEntity::getSentiment).setHeader("Sentiment").setWidth("100px");
        grid.addColumn(e->{
            if(e.getCreatedAt() !=null){
                return formatter.format(e.getCreatedAt());
            }
            return "";
        }).setHeader("Created At").setWidth("100px");

        loadItems();
    }

    private void loadItems() {

        List<SentimentFeedEntity> allSentiments = sentimentService.getAllSentiments();
        this.grid.setItems(allSentiments);
    }
}
