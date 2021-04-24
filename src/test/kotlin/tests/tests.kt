package tests

import models.JSONArray
import models.JSONNumber
import models.JSONObject
import models.JSONString
import visitor.JSONVisitor



fun main() {
    var notaValor = JSONNumber(2)
    var parchavevalor_nota = JSONObject.JSONKeyValuePair("nota", notaValor)

    var nome = JSONString("Miguel")
    var parchavevalor_nome = JSONObject.JSONKeyValuePair("nome", nome)

    var nota = JSONObject(mutableListOf(parchavevalor_nota, parchavevalor_nome, ))

    var arrayJSON = JSONArray(mutableListOf(nota, notaValor, nome))


    var jsonGenerator = JSONVisitor()
    nota.accept(jsonGenerator)
    println(jsonGenerator.jsonString)

    var jsonGenerator2 = JSONVisitor()
    arrayJSON.accept(jsonGenerator2)
    println(jsonGenerator2.jsonString)
}
