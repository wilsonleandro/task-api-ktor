package br.com.task.utils

enum class SuccessCodes(val message: String) {
    REGISTER_TASK("Tarefa cadastrada com sucesso"),
    UPDATE_TASK("Tarefa atualizada com sucesso"),
    DELETE_TASK("Tarefa deletada com sucesso"),
    COMPLETE_TASK("Tarefa concluída com sucesso"),
}
