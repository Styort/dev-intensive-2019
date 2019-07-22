package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.RETRY -> Question.RETRY.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {

        val validationResult = Validation(answer)

        if (validationResult.isEmpty()) {
            return if (question.answer.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            } else {
                if (question == Question.IDLE)
                    return question.question to status.color

                if(status == Status.CRITICAL){
                    question = Question.RETRY
                    status = Status.NORMAL
                    return question.question to status.color
                }

                status = status.nextStatus()

                if(question == Question.RETRY)
                    question = Question.NAME

                return "Это неправильный ответ\n${question.question}" to status.color
            }
        }
        return "$validationResult\n${question.question}" to status.color
    }

    fun Validation(answer: String) = when (question) {
        Question.NAME -> if (answer.isBlank() || answer[0].isLowerCase()) "Имя должно начинаться с заглавной буквы" else ""
        Question.RETRY -> if (answer.isBlank() || answer[0].isLowerCase()) "Имя должно начинаться с заглавной буквы" else ""
        Question.PROFESSION -> if (answer.isBlank() || answer[0].isUpperCase()) "Профессия должна начинаться со строчной буквы" else ""
        Question.MATERIAL -> if (answer.isBlank() || answer.contains(Regex("\\d"))) "Материал не должен содержать цифр" else ""
        Question.BDAY -> if (answer.isBlank() || !answer.isDigitsOnly()) "Год моего рождения должен содержать только цифры" else ""
        Question.SERIAL -> if (answer.isBlank() || !answer.isDigitsOnly() || answer.length != 7) "Серийный номер содержит только цифры, и их 7" else ""
        else -> ""
    }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));


        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answer: List<String>) {
        RETRY("Это неправильный ответ. Давай все по новой\nКак меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "wood", "iron")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}
