const placeStore = {
  namespaced: true,
  state: {
    place: {},
  },
  getters: {
    checkPlace: function (state) {
      return state.place;
    },
  },
  mutations: {
    SET_PLACE: (state, place) => {
      state.place = place;
    },
  },
  actions: {
    selectPlace({ commit }, place) {
      commit("SET_PLACE", place);
    },
  },
};
export default placeStore;
