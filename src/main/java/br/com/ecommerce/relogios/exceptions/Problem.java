package br.com.ecommerce.relogios.exceptions;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response.Status;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Problem {

    private int status;
    private OffsetDateTime timestamp;
    private String title;
    private String detail;
    private List<ProblemObject> messages;

    public Problem(ConstraintViolationException e) {
        this.status = Status.BAD_REQUEST.getStatusCode();
        this.timestamp = OffsetDateTime.now();
        this.title = Status.BAD_REQUEST.getReasonPhrase();
        this.detail = "Dados inválidos";
        this.messages = new ArrayList<>();
    }

    public Problem(IllegalArgumentException e) {
        this.status = Status.BAD_REQUEST.getStatusCode();
        this.timestamp = OffsetDateTime.now();
        this.title = Status.BAD_REQUEST.getReasonPhrase();
        this.detail = "Dados inválidos";
        this.messages = new ArrayList<>();
    }

    public Problem(NullPointerException e) {
        this.status = Status.INTERNAL_SERVER_ERROR.getStatusCode();
        this.timestamp = OffsetDateTime.now();
        this.title = Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
        this.detail = "Ocorreu um erro inesperado no servidor. Por favor, tente novamente mais tarde.";
        this.messages = new ArrayList<>();
    }

    public Problem(NotFoundException e) {
        this.status = Status.NOT_FOUND.getStatusCode();
        this.timestamp = OffsetDateTime.now();
        this.title = Status.NOT_FOUND.getReasonPhrase();
        this.detail = "Objeto não encontrado";
        this.messages = new ArrayList<>();
    }

    public Problem(EmailAlreadyInUseException e) {
        this.status = Status.CONFLICT.getStatusCode();
        this.timestamp = OffsetDateTime.now();
        this.title = "Email Já em Uso";
        this.detail = "O endereço de email informado já está em uso. Por favor, utilize outro email.";
        this.messages = new ArrayList<>(Collections.singletonList(new ProblemObject("email", "O endereço de email já está em uso.")));
    }
    public Problem(ValidationException e){
        this.status = Status.BAD_REQUEST.getStatusCode();
        this.timestamp = OffsetDateTime.now();
        this.title = Status.BAD_REQUEST.getReasonPhrase();
        this.detail = "Dados inválidos";
        this.messages = new ArrayList<>();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<ProblemObject> getMessages() {
        return messages;
    }

    public void setMessages(List<ProblemObject> messages) {
        this.messages = messages;
    }
}
