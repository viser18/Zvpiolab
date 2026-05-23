package com.example.server.models;

public class TicketResponse {
    private Ticket ticket;
    private String signature;

    public TicketResponse() {}

    public TicketResponse(Ticket ticket, String signature) {
        this.ticket = ticket;
        this.signature = signature;
    }

    public Ticket getTicket() { return ticket; }
    public String getSignature() { return signature; }

    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public void setSignature(String signature) { this.signature = signature; }

    public static TicketResponseBuilder builder() {
        return new TicketResponseBuilder();
    }

    public static class TicketResponseBuilder {
        private Ticket ticket;
        private String signature;

        public TicketResponseBuilder ticket(Ticket ticket) { this.ticket = ticket; return this; }
        public TicketResponseBuilder signature(String signature) { this.signature = signature; return this; }

        public TicketResponse build() {
            return new TicketResponse(ticket, signature);
        }
    }
}