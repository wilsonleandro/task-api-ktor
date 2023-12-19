package br.com.task.utils

enum class ErrorCodes(val message: String) {
    REGISTER_TASK("Não foi possível cadastrar a tarefa"),
    UPDATE_TASK("Não foi possível atualizar a tarefa"),
    DELETE_TASK("Não foi possível deletar a tarefa"),
    TASK_NOT_FOUND("Nenhuma tarefa encontrada"),
    EMPTY_FIELDS("Preencha todos os campos"),
    COMPLETE_TASK("Não foi possível concluir a tarefa"),
}