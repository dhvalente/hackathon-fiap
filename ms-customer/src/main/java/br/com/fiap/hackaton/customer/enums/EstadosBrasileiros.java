package br.com.fiap.hackaton.customer.enums;

import java.text.Normalizer;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EstadosBrasileiros {
    ACRE("AC", "Acre", "Acre", 1),
    ALAGOAS("AL", "Alagoas", "Alagoas", 2),
    AMAPA("AP", "Amapá", "Amapa", 3),
    AMAZONAS("AM", "Amazonas", "Amazonas", 4),
    BAHIA("BA", "Bahia", "Bahia", 5),
    CEARA("CE", "Ceará", "Ceara", 6),
    FEDERAL_DISTRICT("DF", "Distrito Federal", "Distrito Federal", 7),
    ESPIRITO_SANTO("ES", "Espírito Santo", "Espirito Santo", 8),
    GOIAS("GO", "Goiás", "Goias", 9),
    MARANHAO("MA", "Maranhão", "Maranhao", 10),
    MATO_GROSSO("MT", "Mato Grosso", "Mato Grosso", 11),
    MATO_GROSSO_DO_SUL("MS", "Mato Grosso do Sul", "Mato Grosso do Sul", 12),
    MINAS_GERAIS("MG", "Minas Gerais", "Minas Gerais", 13),
    PARA("PA", "Pará", "Para", 14),
    PARAIBA("PB", "Paraíba", "Paraiba", 15),
    PARANA("PR", "Paraná", "Parana", 16),
    PERNAMBUCO("PE", "Pernambuco", "Pernambuco", 17),
    PIAUI("PI", "Piauí", "Piaui", 18),
    RIO_DE_JANEIRO("RJ", "Rio de Janeiro", "Rio de Janeiro", 19),
    RIO_GRANDE_DO_NORTE("RN", "Rio Grande do Norte", "Rio Grande do Norte", 20),
    RIO_GRANDE_DO_SUL("RS", "Rio Grande do Sul", "Rio Grande do Sul", 21),
    RONDONIA("RO", "Rondônia", "Rondonia", 22),
    RORAIMA("RR", "Roraima", "Roraima", 23),
    SANTA_CATARINA("SC", "Santa Catarina", "Santa Catarina", 24),
    SAO_PAULO("SP", "São Paulo", "Sao Paulo", 25),
    SERGIPE("SE", "Sergipe", "Sergipe", 26),
    TOCANTINS("TO", "Tocantins", "Tocantins", 27);

    private final String abbreviation;
    private final String description;
    private final String descriptionWithoutSpecialCharacters;
    private final Integer id;

    EstadosBrasileiros(String abbreviation, String description, String descriptionWithoutSpecialCharacters, int id) {
        this.abbreviation = abbreviation;
        this.description = description;
        this.descriptionWithoutSpecialCharacters = normalizeString(descriptionWithoutSpecialCharacters);
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionWithoutSpecialCharacters() {
        return descriptionWithoutSpecialCharacters;
    }

    public Integer getId() {
        return id;
    }

    public static String normalizeString(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutAccents = normalized.replaceAll("\\p{M}", "");
        return withoutAccents.substring(0, 1).toUpperCase() + withoutAccents.substring(1).toLowerCase();
    }
    private static final Map<String, EstadosBrasileiros> Lookup = new HashMap<>();

    static {
        for (EstadosBrasileiros keyValue : EnumSet.allOf(EstadosBrasileiros.class))
            Lookup.put(keyValue.getDescriptionWithoutSpecialCharacters(), keyValue);
    }

    public static EstadosBrasileiros get(String description) {
        return Lookup.get(normalizeString(description));
    }

    public static EstadosBrasileiros getBrazilianStateByAbbreviation(String abbreviation) {
        for (EstadosBrasileiros keyValue : EnumSet.allOf(EstadosBrasileiros.class))
            if (keyValue.getAbbreviation().equals(abbreviation))
                return keyValue;
        return null;
    }
}