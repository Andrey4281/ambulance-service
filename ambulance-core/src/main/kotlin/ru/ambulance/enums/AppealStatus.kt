package ru.ambulance.enums

/**
 * Статус обращения пациента
 */
enum class AppealStatus {
    /**
     * Новое обращение
     */
    NEW,
    /**
     * Обращение назначено на врача
     */
    ASSIGNED,
    /**
     * Доктор пациента доктором
     */
    IN_PROGRESS,
    /**
     * Пациент сдает анализы
     */
    INVESTIGATION_TREATMENT,
    /**
     * Доктор готов для вынесения вердикта пациенту
     */
    READY_FOR_VERDICT,
    /**
     * Пациент госпитализирован
     */
    GOSPITALIZED,
    /**
     * Пациент отпущен домой
     */
    RELEASED,
    /**
     * Пациент отказался от госпитализации
     */
    REJECTED_FROM_GOSPITALIZATION,
    /**
     * Обращение упало с системной ошибкой
     */
    ERROR,
    /**
     * Обращение упало с ошибкой по причине недоступности врачей
     */
    UNAVAILABLE_DOCTOR
}