package com.example.mypokedex.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val isDefault: Boolean,
    val order: Int,
    val weight: Int,
    val sprites: Sprites,
    val abilities: List<Ability>,
    val forms: List<Form>,
    val gameIndices: List<GameIndex>,
    val heldItems: List<HeldItem>,
    val locationAreaEncounters: String,
    val moves: List<Move>,
    val species: Species,
    val stats: List<Stat>,
    val types: List<Type>,
    val flavorTextEntries: List<FlavorTextEntry>,
    var spanishFlavorTextEntries: List<String> = emptyList()
)

data class FlavorTextEntry(
    val flavorText: String,
    val language: Language
)

data class Language(
    val name: String,
    val url: String
)

data class Sprites(
    val backDefault: String?,
    val backFemale: String?,
    val backShiny: String?,
    val backShinyFemale: String?,
    val frontDefault: String?,
    val frontFemale: String?,
    val frontShiny: String?,
    val frontShinyFemale: String?
)

data class Ability(
    val ability: AbilityInfo,
    val isHidden: Boolean,
    val slot: Int
)

data class PokeResult (
    val name: String,
    val url: String
)

data class AbilityInfo(
    val name: String,
    val url: String
)

data class Form(
    val name: String,
    val url: String
)

data class GameIndex(
    val gameIndex: Int,
    val version: Version
)

data class Version(
    val name: String,
    val url: String
)

data class HeldItem(
    val item: Item,
    val versionDetails: List<VersionDetail>
)

data class Item(
    val name: String,
    val url: String
)

data class VersionDetail(
    val rarity: Int,
    val version: Version
)

data class Move(
    val move: MoveInfo,
    val versionGroupDetails: List<VersionGroupDetail>
)

data class MoveInfo(
    val name: String,
    val url: String
)

data class VersionGroupDetail(
    val levelLearnedAt: Int,
    val moveLearnMethod: MoveLearnMethod,
    val versionGroup: VersionGroup
)

data class MoveLearnMethod(
    val name: String,
    val url: String
)

data class VersionGroup(
    val name: String,
    val url: String
)

data class Species(
    val name: String,
    val url: String
)

data class Stat(
    val baseStat: Int,
    val effort: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String,
    val url: String
)

data class Type(
    val slot: Int,
    val type: TypeInfo
)

data class TypeInfo(
    val name: String,
    val url: String
)