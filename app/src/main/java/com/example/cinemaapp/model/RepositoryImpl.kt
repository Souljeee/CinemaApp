package com.example.cinemaapp.model

class RepositoryImpl : Repository {
    override fun getCinemasFromServer() : List<Cinema> {
        val dto = CinemaRepo.api.getCinemas("37a33c74592d522dfdc42d090c29c4bf","ru").execute().body()
        val popularCinemaList  = mutableListOf<Cinema>()
        for(i in 0 until dto?.results?.size!!){
            popularCinemaList.add(toCinema(dto.results[i]))
        }
        return popularCinemaList
    }

    override fun getCinemaFromServer(id:Int): Cinema {
        val dto: CinemaDTO? = CinemaRepo.api.getCinema(id,"37a33c74592d522dfdc42d090c29c4bf","ru").execute().body()
        return Cinema(
            id = dto?.id,
            name = dto?.title,
            releaseDate = dto?.release_date,
            poster = dto?.poster_path,
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
            poster = cinemaDTO.poster_path.toString(),
            rating = cinemaDTO.vote_average.toString(),
            description = cinemaDTO.overview.toString()
        )
    }


}