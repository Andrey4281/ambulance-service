package ru.ambulance.enums

/**
 * Состояние пациента
 */
enum class PatientState {
    /**
     * Удовлетворительное состояние
     */
    SATISFACTORY,
    /**
     * Состояние средней степени тяжести
     */
    MODERATE_SEVERITY,
    /**
     * Тяжелое состояние
     */
    SERIOUS,
    /**
     * Крайне тяжелое
     */
    EXTREMELY_SERIOUS,
    /**
     * Агония
     */
    AGONY,
    /**
     * Клиническая смерть
     */
    CLINICAL_DEATH,
    /**
     * Биологическая смерть
     */
    BIOLOGICAL_DEATH
}