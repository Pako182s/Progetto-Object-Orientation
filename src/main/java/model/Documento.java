package model;

/**
 * Classe che rappresenta un documento associato a un team nell'hackathon.
 * Contiene il contenuto del documento e i commenti dei giudici.
 *

 */
public class Documento {
    private String documento;
    private String commentoGiudice;

    /**
     * Costruttore per creare un nuovo documento.
     *
     * @param documento il contenuto testuale del documento
     * @param commentoGiudice il commento del giudice sul documento
     */
    public Documento(String documento, String commentoGiudice) {
        this.documento = documento;
        this.commentoGiudice = commentoGiudice;
    }
}