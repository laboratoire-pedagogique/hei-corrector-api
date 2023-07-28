export function isHidingAnOption(input) {
    const animalIndex = input.indexOf('A');
    const hidingSpotIndex = input.indexOf('C');

    if (hidingSpotIndex === -1) {
        return false; // No hiding spots available
    }

    const animalToHidingSpot = Math.abs(animalIndex - hidingSpotIndex);
    const youToHidingSpot = hidingSpotIndex;

    return animalToHidingSpot > youToHidingSpot;
}
