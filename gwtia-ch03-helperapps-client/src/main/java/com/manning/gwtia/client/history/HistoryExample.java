package com.manning.gwtia.client.history;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class HistoryExample extends Composite implements ValueChangeHandler<String> {

    final String CONTACT = "Contact";
    final String HOME = "Home";
    final String PRODUCTS = "Products";
    final String BACK = "History Back";
    final String FORWARD = "History Forward";

    ScrollPanel tokenDisplayHolder;

    FlowPanel tokenDisplay;

    Button home;
    Button products;
    Button contact;

    Button back;
    Button forward;

    private FlowPanel setUpGui() {
        FlowPanel toolbar = new FlowPanel();

        FlowPanel histories = new FlowPanel();
        home = new Button(HOME);
        products = new Button(PRODUCTS);
        contact = new Button(CONTACT);

        histories.add(home);
        histories.add(products);
        histories.add(contact);

        FlowPanel directions = new FlowPanel();
        back = new Button(BACK);
        forward = new Button(FORWARD);

        directions.add(back);
        directions.add(forward);

        toolbar.add(histories);
        toolbar.add(directions);

        tokenDisplay = new FlowPanel();
        tokenDisplayHolder = new ScrollPanel();
        toolbar.add(tokenDisplayHolder);
        tokenDisplay.setSize("100%", "100%");
        tokenDisplay.getElement().getStyle().setBackgroundColor("#44ffff");
        tokenDisplayHolder.setSize("200px", (Window.getClientHeight() - 50) + "px");
        tokenDisplayHolder.add(tokenDisplay);
        return toolbar;
    }

    private void setUpEventHandling() {
        home.addClickHandler(event -> History.newItem(HOME));

        products.addClickHandler(event -> History.newItem(PRODUCTS));

        contact.addClickHandler(event -> History.newItem(CONTACT));

        back.addClickHandler(event -> History.back());

        forward.addClickHandler(event -> History.forward());
    }

    public void setUpHistoryManagement() {
        History.addValueChangeHandler(this);
        History.fireCurrentHistoryState();
        Window.addWindowClosingHandler(event ->
                event.setMessage("Ran out of history.  Now leaving application, is that OK?"));
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        String token = null;

        if (event.getValue() != null) {
            token = event.getValue().trim();
        }

        if ((token == null) || (token.equals(""))) {
            showHomePage();
        } else if (token.equals(PRODUCTS)) {
            showProducts();
        } else if (token.equals(CONTACT)) {
            showContact();
        } else {
            showHomePage();
        }
    }


    /**
     * Show the contact page - i.e. place a new label on the current screen
     */
    private void showContact() {
        showToken(CONTACT);
    }

    /**
     * Show the home page - i.e. place a new label on the current screen
     */
    private void showHomePage() {
        showToken(HOME);
    }

    /**
     * Show the products page - i.e. place a new label on the current screen
     */
    private void showProducts() {
        showToken(PRODUCTS);
    }


    /**
     * Simple helper method to show a string on the screen
     * @param token
     */
    private void showToken(String token) {
        if (tokenDisplay != null) {
            Label newItem = new Label(token);
            tokenDisplay.add(newItem);
            tokenDisplayHolder.ensureVisible(newItem);
        }
    }

    public HistoryExample() {
        setUpHistoryManagement();
        initWidget(setUpGui());
        setUpEventHandling();
    }
}
