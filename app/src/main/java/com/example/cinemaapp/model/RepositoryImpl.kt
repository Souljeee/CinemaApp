package com.example.cinemaapp.model

class RepositoryImpl : Repository {
    override fun getCinemasFromServer() : List<Cinema> {
        val dto = CinemaLoader.loadCinemas()
        val popularCinemaList  = mutableListOf<Cinema>()
        for(i in 0 until dto?.results?.size!!){
            popularCinemaList.add(toCinema(dto.results[i]))
        }
        return popularCinemaList
    }

    override fun getCinemaFromServer(id:Int): Cinema {
        val dto: CinemaDTO? = CinemaLoader.loadCinema(id)
        return Cinema(
            id = dto?.id,
            name = dto?.title,
            releaseDate = dto?.release_date,
            revenue = "${dto?.revenue}$",
            rating =  "${dto?.vote_average}(${dto?.vote_count})",
            duration = "${dto?.runtime} мин",
            description = dto?.overview,
        )

    }

    private fun toCinema(cinemaDTO: CinemaDTO): Cinema{
        return Cinema(
            id = cinemaDTO.id,
            name = cinemaDTO.title.toString(),
            releaseDate = cinemaDTO.release_date.toString(),
            rating = cinemaDTO.vote_average.toString(),
            description = cinemaDTO.overview.toString()
        )
    }

    override fun getCinemaFromLocalStorage() = getDefaultCinemas()
}