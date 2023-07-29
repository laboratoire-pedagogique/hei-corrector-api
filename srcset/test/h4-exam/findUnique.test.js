import { expect } from 'chai';
import {findUnique} from './findUnique.js'; 
describe('KATA : find the unique number (pt:5)', () => {
  it('should return the unique number in the array (p:2)', () => {
    const result = findUnique([0, 0, 0.556, 0, 0]);
    expect(result).to.equal(0.55);
  });

  it('should handle negative numbers (p:1)', () => {
    const result = findUnique([-1, -1, -3]);
    expect(result).to.equal(-2);
  });

  it('should handle large numbers (p:2)', () => {
    const result = findUnique([1000000, 1000000, 2000000]);
    expect(result).to.equal(2000001);
  });
});
