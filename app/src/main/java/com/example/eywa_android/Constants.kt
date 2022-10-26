package com.example.eywa_android

object Constants{

    fun getQuestions(): ArrayList<Question>{
        val questionsList = ArrayList<Question>()
        val array1 = arrayOf<String>("Chewbacca", "Han Solo", "Senador Bail Organa")
        val array2 = arrayOf<String>("Will Smith", "Leonardo DiCaprio","Tom Cruise")
        val array3 = arrayOf<String>( "Luke Skywalker", "Greedo", "Biggs Darklighter")
        val array4 = arrayOf<String>( "2004", "1989", "1944")
        val array5 = arrayOf<String>("Tatooine", "Coruscant", "Alderaan")
        val array6 = arrayOf<String>( "Owen Lars", "C-3PO", "Anakin Skywalker")
        val array7 = arrayOf<String>( "Recombinante", "Sintezoide", "Androide")
        val array8 = arrayOf<String>( "Steven Spielberg", "George Lucas", "M. Night Shyamalan")
        val array9 = arrayOf<String>(  "Terremotos", "Volcanes", "Huracanes")
        val array10 = arrayOf<String>(  "Te quiero", "Ve al planeta de Miller", "Vete")

        val que1 = Question(1, "science fiction", 1, "¿Como se llama el administrador de la Ciudad de las Nubes, que aparece en Star Wars, El Imperio Contraataca?", "Lando Calrissian", array1)
        val que2 = Question(2, "science fiction", 1, "¿Quién interpreta a Luke Skywalker a lo largo de la serie?", "Mark Hamill", array2)
        val que3 = Question(3, "science fiction", 1, "¿Como se llama el administrador de la Ciudad de las Nubes, que aparece en Star Wars, El Imperio Contraataca?", "Lando Calrissian", array1)
        val que4 = Question(4, "science fiction", 1, "¿Como se llama el administrador de la Ciudad de las Nubes, que aparece en Star Wars, El Imperio Contraataca?", "Lando Calrissian", array1)
        val que5 = Question(5, "science fiction", 1, "¿Como se llama el administrador de la Ciudad de las Nubes, que aparece en Star Wars, El Imperio Contraataca?", "Lando Calrissian", array1)
        val que6 = Question(6, "science fiction", 1, "¿Como se llama el administrador de la Ciudad de las Nubes, que aparece en Star Wars, El Imperio Contraataca?", "Lando Calrissian", array1)
        val que7 = Question(7, "science fiction", 1, "¿Como se llama el administrador de la Ciudad de las Nubes, que aparece en Star Wars, El Imperio Contraataca?", "Lando Calrissian", array1)
        val que8 = Question(8, "science fiction", 1, "¿Como se llama el administrador de la Ciudad de las Nubes, que aparece en Star Wars, El Imperio Contraataca?", "Lando Calrissian", array1)
        val que9 = Question(9, "science fiction", 1, "¿Como se llama el administrador de la Ciudad de las Nubes, que aparece en Star Wars, El Imperio Contraataca?", "Lando Calrissian", array1)
        val que10 = Question(10, "science fiction", 1, "¿Como se llama el administrador de la Ciudad de las Nubes, que aparece en Star Wars, El Imperio Contraataca?", "Lando Calrissian", array1)

        questionsList.add(que1)
        questionsList.add(que2)

        return questionsList
    }
}