package com.example.eywa_android

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader
import java.io.FileWriter

class FilesManager {
    companion object
    {
        fun getUsers(context:Context): MutableList<User> {
            val usersJsonFilePath = context.filesDir.toString() + "/json/users_android.json"
            val usersJsonFile = FileReader(usersJsonFilePath)
            val listUsersType = object: TypeToken<MutableList<User>>() {}.type
            val users : MutableList<User> = Gson().fromJson(usersJsonFile, listUsersType)
            return users

        }

        fun getQuestionsCA(context:Context): MutableList<Question> {
            val questionsCaJsonFilePath = context.filesDir.toString() + "/json/questions_ca.json"
            val questionsCaJsonFile = FileReader(questionsCaJsonFilePath)
            val listQuestionsCaType = object: TypeToken<MutableList<Question>>(){}.type
            val questionsCA : MutableList<Question> = Gson().fromJson(questionsCaJsonFile,listQuestionsCaType)
            return questionsCA
        }
        fun getQuestionsEN(context:Context): MutableList<Question> {
            val questionsEnJsonFilePath = context.filesDir.toString() + "/json/questions_en.json"
            val questionsEnJsonFile = FileReader(questionsEnJsonFilePath)
            val listQuestionsEnType = object: TypeToken<MutableList<Question>>(){}.type
            val questionsEn : MutableList<Question> = Gson().fromJson(questionsEnJsonFile,listQuestionsEnType)
            return questionsEn
        }
        fun getQuestionsES(context:Context): MutableList<Question> {
            val questionsEsJsonFilePath = context.filesDir.toString() + "/json/questions_es.json"
            val questionsEsJsonFile = FileReader(questionsEsJsonFilePath)
            val listQuestionsEsType = object: TypeToken<MutableList<Question>>(){}.type
            val questionsEs : MutableList<Question> = Gson().fromJson(questionsEsJsonFile,listQuestionsEsType)
            return questionsEs
        }
        fun getCharacters(context:Context): MutableList<Characters> {
            val charactersJsonFilePath = context.filesDir.toString() + "/json/characters.json"
            val charactersJsonFile = FileReader(charactersJsonFilePath)
            val listCharactersType = object: TypeToken<MutableList<Characters>>() {}.type
            val characters : MutableList<Characters> = Gson().fromJson(charactersJsonFile, listCharactersType)
            return characters

        }


        fun saveUser(context:Context, users : MutableList<User>){
            val jsonFilePath = context.filesDir.toString() + "/json/users_android.json"
            val jsonFile = FileWriter(jsonFilePath)
            var gson = Gson()
            var jsonElement = gson.toJson(users)
            jsonFile.write(jsonElement)
            jsonFile.close()
        }
    }
}