<template>
  <div>
    <div id="map" style="width: 50%; height: 600px"></div>
  </div>
</template>

<script>
export default {
  mounted() {
    this.$nextTick(() => {
      this.initMap();
    });
  },
  data() {
    return {
      center: { lat: 37.77, lng: -122.447 },
    };
  },
  methods: {
    initMap() {
      const directionsRenderer = new google.maps.DirectionsRenderer();
      const directionsService = new google.maps.DirectionsService();
      const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 14,
        center: { lat: 37.77, lng: -122.447 },
      });

      directionsRenderer.setMap(map);
      this.calculateAndDisplayRoute(directionsService, directionsRenderer);
    },

    calculateAndDisplayRoute(directionsService, directionsRenderer) {
      directionsService
        .route({
          origin: { lat: 37.77, lng: -122.447 },
          destination: { lat: 37.768, lng: -122.511 },
          travelMode: "DRIVING",
        })
        .then((response) => {
          directionsRenderer.setDirections(response);
        })
        .catch((e) => window.alert("Directions request failed due to " + status));
    },
  },
};
</script>
