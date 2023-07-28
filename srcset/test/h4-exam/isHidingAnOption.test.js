import { expect } from 'chai';
import {isHidingAnOption} from './isHidingAnOption.js';

describe('KATA : is hiding an option ? (pt:6)', () => {
    it('should return true when you can reach a hiding spot before the animal (p:1)', () => {
        const result = isHidingAnOption('X..C....A..');
        expect(result).to.be.true;
    });

    it('should return false when reaching a hiding spot is not an option (p:1)', () => {
        const result = isHidingAnOption('X...C..A');
        expect(result).to.be.false;
    });

    it('should return false when reaching a hiding spot is not an option (p:1)', () => {
        const result = isHidingAnOption('X....A.C.....C.C');
        expect(result).to.be.false;
    });

    it('should return false when there are no hiding spots (p:1)', () => {
        const result = isHidingAnOption('X.....A');
        expect(result).to.be.false;
    });

    it('should return false when you reach the hiding stop at the same time as the animal (p:1)', () => {
        const result = isHidingAnOption('X..C..A');
        expect(result).to.be.false;
    });

    it('should return false when there are multiple hiding spots but reaching any of them is not an option (p:1)', () => {
        const result = isHidingAnOption('X.....AC...C');
        expect(result).to.be.false;
    });
});
